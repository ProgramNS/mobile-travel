package com.reza.travel.activity.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public AboutViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("NILA TRANSPORT adalah sebuah usaha yang bergerak dalam bidang jasa transportasi darat, "
                + "khususnya untuk melayani angkutan kota maupun luar kota di sektor pariwisata. Target pasar kami mencakup individu, keluarga, perusahaan, dan acara khusus.\n\n"
                + "Usaha ini dimulai pada awal Januari 2024 di Daerah Istimewa Yogyakarta. NILA TRANSPORT menyediakan jasa penyewaan armada Hiace Commuter dan Hiace Premio dengan kapasitas 14 hingga 15 seat, "
                + "dengan fokus utama pada pelayanan pariwisata di area Yogyakarta. Kami berkomitmen untuk memberikan kenyamanan dan kepuasan maksimal kepada pelanggan dalam kegiatan berwisata, terutama di destinasi wisata di Yogyakarta.\n\n"
                + "Selain itu, kami juga melayani kebutuhan transportasi untuk kegiatan dinas maupun acara pribadi dan keluarga. "
                + "Dalam menjalankan bisnis ini, kami mengutamakan kualitas pelayanan dengan fokus pada keamanan dan kepuasan pelanggan, sesuai dengan kebutuhan dan harapan mereka.\n\n"
                + "Kami menyadari bahwa dunia tempat kami beroperasi terus berubah dan berkembang dengan pesat, begitu pula kebutuhan dan preferensi pelanggan. "
                + "Konsumen semakin selektif dalam membuat keputusan pembelian, dan mereka menuntut layanan yang lebih baik dan berkualitas. "
                + "Oleh karena itu, kami terus berupaya untuk mengembangkan dan meningkatkan layanan kami, serta mempromosikan jasa NILA TRANSPORT dengan sebaik-baiknya.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}