package com.tufar.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tufar.besinlerkitabi.model.Besin
import com.tufar.besinlerkitabi.service.BesinAPIService
import com.tufar.besinlerkitabi.service.BesinDatabase
import com.tufar.besinlerkitabi.utilities.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {
    val besinler = MutableLiveData<List<Besin>>()
    val besinHataMessage = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    private var guncellemeZamani = 10*60*1000*1000*1000L // nano time' ı dakikaya çevirmek için çarpmamız gereken sayılar
    private val besinApiService = BesinAPIService()
    private val disposable = CompositeDisposable()
    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData(){
        val kaydedilmeZamani = ozelSharedPreferences.zamaniAl()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && System.nanoTime() - kaydedilmeZamani < guncellemeZamani) {
            // Sqlite'tan Çek
            verileriSqlitetanAl()
        }
        else{
            verileriInternettenAl()
        }
    }
    fun refreshFromInternet(){
        verileriInternettenAl()
    }
    private fun verileriSqlitetanAl(){
        besinYukleniyor.value = true

        launch {
            val besinListesi = BesinDatabase(getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(),"Verileri Roomdan Aldık!",Toast.LENGTH_LONG).show()
        }
    }
    private fun verileriInternettenAl(){
        besinYukleniyor.value = true

        // IO, Default, UI  Threadleri (Alt İşlemleri)

        disposable.add(
            besinApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>(){
                    override fun onSuccess(t: List<Besin>) {
                        sqliteSakla(t)
                        Toast.makeText(getApplication(),"Verileri İnternetten Aldık!",Toast.LENGTH_LONG).show()

                    }

                    override fun onError(e: Throwable) {
                        besinHataMessage.value = true
                        besinYukleniyor.value = false
                        e.printStackTrace()
                    }

                })
        )

    }

    private fun besinleriGoster(besinlerListesi : List<Besin>){
        besinler.value = besinlerListesi
        besinHataMessage.value = false
        besinYukleniyor.value = false

    }

    private fun sqliteSakla(besinListesi: List<Besin>)
    {
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAllBesin()
            val uuidList = dao.insertAll(*besinListesi.toTypedArray())  // Liste içerisindeki elemanları tek tek insert ediyor

            var i = 0
            while (i< besinListesi.size){
                besinListesi[i].uuid = uuidList[i].toInt()
                i += 1
            }
            besinleriGoster(besinListesi)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}