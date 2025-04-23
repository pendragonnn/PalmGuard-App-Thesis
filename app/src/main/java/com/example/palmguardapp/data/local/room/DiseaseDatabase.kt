package com.example.palmguardapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.palmguardapp.data.local.entity.DiseaseDiagnose
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [DiseaseDiagnose::class], version = 1)
abstract class DiseaseDatabase : RoomDatabase() {
    abstract fun diseaseDiagnoseDao(): DiseaseDiagnoseDao

    companion object {
        @Volatile
        private var INSTANCE : DiseaseDatabase? = null

        @JvmStatic
        fun getDatabase(context : Context) : DiseaseDatabase  {
            if (INSTANCE == null) {
                synchronized(DiseaseDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        DiseaseDatabase::class.java, "disease_diagnose_database")
                        .addCallback(DatabaseCallback())
                        .build()
                }
            }
            return INSTANCE as DiseaseDatabase
        }
    }

    private class DatabaseCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { diseaseDatabase ->
                CoroutineScope(Dispatchers.IO).launch {
                    populateDatabase(diseaseDatabase.diseaseDiagnoseDao())
                }
            }
        }

        private suspend fun populateDatabase(dao: DiseaseDiagnoseDao) {
            val diseaseData = listOf(
                DiseaseDiagnose(
                    id = "D-001",
                    diseaseName = "Brown Spots",
                    diseaseExplanation = "Penyakit bercak coklat pada daun kelapa sawit merupakan masalah serius di fase pembibitan yang disebabkan oleh jamur patogen Curvularia sp.,. Gejalanya berupa bercak kekuningan hingga cokelat pada daun yang membesar, menyebabkan jaringan daun mati, mengering, dan akhirnya mengganggu fotosintesis serta pertumbuhan bibit. Jika tidak ditangani, infeksi ini dapat menurunkan kualitas tanaman, menyebar ke tanaman sehat, bahkan menyebabkan kematian tanaman dengan intensitas serangan yang signifikan.",
                    diseaseRecommendation = "Penanganan penyakit bercak coklat pada daun kelapa sawit dilakukan secara terpadu melalui pendekatan kimiawi, hayati, fisik, dan budaya. Fungisida sintetis seperti difenokonazol dan mankozeb efektif digunakan, namun perlu dibarengi dengan alternatif ramah lingkungan seperti agen hayati Trichoderma sp., ekstrak kulit jengkol, asap cair sabut pinang, dan iradiasi sinar UV atau LED. Upaya budaya seperti menjaga sanitasi, mengatur jarak tanam, serta penyiraman yang tepat juga penting untuk mencegah penyebaran penyakit.",
                    detailExplanation = "Penyakit bercak coklat pada daun kelapa sawit (Elaeis guineensis Jacq.) merupakan salah satu kendala utama dalam fase pembibitan yang umum dijumpai di sentra-sentra perkebunan, dan umumnya disebabkan oleh infeksi jamur patogen Curvularia sp.,. Infeksi ini ditandai dengan munculnya bercak-bercak kecil berwarna kuning tembus cahaya hingga kekuningan atau cokelat pada permukaan daun, yang lama-kelamaan membesar dengan pusat bercak berwarna coklat muda dan tepi jingga kekuningan atau tidak beraturan, serta menyebabkan jaringan daun mati, mengering, dan rontok. Daun yang rusak akibat infeksi tersebut mengganggu proses fotosintesis, sehingga menurunkan laju pertumbuhan bibit, menyebabkan tanaman menjadi kerdil, dan menurunkan kualitasnya hingga tidak layak untuk ditanam di lapangan. Serangan yang parah bahkan dapat menyebabkan kematian bibit, serta berpotensi menjadi sumber inokulum yang menyebar ke tanaman sehat lainnya jika tidak ditangani dengan baik.",
                    detailPrevention = "Pencegahan penyakit bercak coklat pada daun kelapa sawit memerlukan pendekatan terpadu yang mencakup aspek biologis, lingkungan, dan budidaya. Salah satu langkah awal yang krusial adalah penggunaan benih berkualitas dan media tanam steril guna mengurangi risiko kontaminasi sejak dini. Sanitasi lahan pembibitan harus dijaga dengan membersihkan gulma, sisa tanaman, dan daun terinfeksi, serta memastikan sirkulasi udara dan drainase yang baik untuk menghindari kelembapan berlebih yang disukai jamur patogen seperti Curvularia sp.. Pengelolaan lingkungan juga mencakup pengaturan jarak tanam yang tepat, rotasi tanaman, dan penyiraman terukur, serta pemberian nutrisi seimbang antara unsur makro dan mikro, menghindari kelebihan nitrogen yang dapat meningkatkan kerentanan tanaman. Pengawasan rutin, terutama saat musim hujan, perlu dilakukan untuk mendeteksi gejala awal penyakit. Selain itu, penggunaan biofungisida alami seperti ekstrak kulit jengkol pada konsentrasi 30% hingga 50% terbukti efektif secara in vitro dalam menekan pertumbuhan koloni dan biomassa Curvularia sp. hingga 100%. Langkah-langkah ini harus didukung dengan edukasi petani mengenai teknik budidaya yang benar serta pengelolaan kebun yang higienis dan terencana sejak awal agar pencegahan penyakit bercak daun dapat berlangsung secara berkelanjutan dan efektif.",
                    detailRecommendation = "Penanganan penyakit bercak coklat pada daun kelapa sawit memerlukan pendekatan terpadu yang mencakup metode kimiawi, hayati, fisik, dan budaya. Secara kimia, fungisida sintetis seperti difenokonazol, mankozeb, klorotalonil, dan tembaga hidroksida sering digunakan karena efektivitasnya dalam menghambat pertumbuhan patogen, meskipun penggunaannya harus bijak untuk menghindari resistensi dan dampak negatif terhadap lingkungan. Sebagai alternatif yang lebih berkelanjutan, diterapkan agen hayati seperti Trichoderma sp. atau sachet mikroba (S2-S4) yang mengandung jamur antagonis dan hormon pertumbuhan untuk meningkatkan ketahanan tanaman. Selain itu, pemanfaatan ekstrak tumbuhan seperti ekstrak kulit jengkol yang mengandung flavonoid, tanin, alkaloid, dan saponin terbukti mampu merusak struktur dan metabolisme jamur. Pendekatan fisik modern seperti iradiasi energi foton dengan sinar UV atau LED, serta penggunaan asap cair dari sabut pinang yang kaya senyawa antijamur alami seperti fenol dan asam asetat, juga efektif menekan pertumbuhan Curvularia sp.. Untuk memperkuat daya tahan tanaman, digunakan pula pupuk organik cair yang dipadukan dengan agen hayati. Secara budaya, upaya pencegahan dilakukan melalui sanitasi lingkungan, pengaturan jarak tanam lebih dari 90 cm untuk mencegah penyebaran penyakit, serta pengelolaan penyiraman yang tepat—menghindari penyiraman langsung ke daun dan mengurangi volume saat musim hujan. Biofungisida seperti CHIPS® juga menjadi pilihan efektif dalam pengendalian penyakit ini secara ramah lingkungan.",
                    date = "2025-02-10"
                ),
                DiseaseDiagnose(
                    id = "D-002",
                    diseaseName = "Healthy",
                    diseaseExplanation = "Daun sawit yang sehat dapat dikenali dengan warna hijau segar yang menunjukkan pasokan klorofil cukup untuk fotosintesis. Daun sehat bebas dari bercak coklat atau hitam, serta kerusakan lain yang bisa mengindikasikan serangan patogen. Struktur daun tampak kokoh, tegak, dan permukaannya mulus tanpa robekan. Tanaman dengan daun sehat menunjukkan pertumbuhan optimal, bebas dari gejala layu atau kekuningan, serta mendapat cukup nutrisi, air, dan sinar matahari. Kesehatan daun sangat penting untuk proses fotosintesis, yang mendukung pertumbuhan dan produktivitas tanaman sawit.",
                    diseaseRecommendation = "Menjaga kesehatan daun sawit membutuhkan perawatan yang teratur dan menyeluruh. Pemupukan yang tepat sangat penting untuk memenuhi kebutuhan unsur hara makro dan mikro tanaman, seperti nitrogen, fosfor, kalium, dan magnesium. Pengairan juga harus dijaga agar pasokan air cukup, karena genangan dapat menyebabkan akar tergenang dan rentan terhadap penyakit. Pemantauan rutin terhadap kondisi tanaman sangat penting untuk mendeteksi gejala penyakit lebih awal, sehingga dapat ditangani sebelum berkembang lebih parah.",
                    detailExplanation = "Daun sawit yang sehat memiliki ciri-ciri yang mudah dikenali. Salah satu indikator utama adalah warnanya yang hijau segar, yang menandakan bahwa tanaman mendapatkan pasokan klorofil yang cukup untuk proses fotosintesis. Daun sawit yang sehat juga bebas dari bercak coklat, hitam, atau tanda-tanda kerusakan lainnya yang bisa menjadi indikasi adanya serangan patogen, seperti jamur atau bakteri. Struktur daun yang sehat juga tampak kokoh dan tegak, dengan permukaan daun yang mulus tanpa adanya robekan atau kerutan. Daun yang sehat akan menunjukkan gejala pertumbuhan yang optimal, dimana pembuluh-pembuluh daun terlihat jelas dan tidak ada gejala layu atau kekuningan. Tanaman sawit yang memiliki daun dalam kondisi seperti ini menandakan bahwa tanaman tersebut memperoleh cukup nutrisi, air, dan sinar matahari, serta terlindung dari infeksi atau stres lingkungan. Kesehatan daun sangat penting karena daun adalah organ utama dalam proses fotosintesis yang berfungsi untuk mendukung pertumbuhan dan produktivitas tanaman sawit.",
                    detailPrevention = "Pencegahan penyakit pada daun sawit yang sehat merupakan langkah yang sangat penting untuk menjaga agar tanaman tetap terlindungi dari infeksi yang dapat merusak pertumbuhannya. Salah satu langkah pertama yang dapat dilakukan adalah dengan menjaga kebersihan kebun. Sisa-sisa tanaman yang telah terinfeksi dapat menjadi tempat berkembang biaknya patogen, seperti jamur atau bakteri, yang bisa menyebar ke tanaman yang sehat. Oleh karena itu, kebersihan kebun harus dijaga dengan baik, termasuk dengan mengumpulkan dan membuang sisa-sisa tanaman yang telah rusak atau terinfeksi. Selain itu, penting untuk menjaga kelembaban tanah dan udara agar tetap seimbang. Tanah yang terlalu lembab atau genangan air yang terus-menerus dapat menciptakan kondisi yang ideal bagi pertumbuhan jamur atau bakteri penyebab penyakit. Oleh karena itu, sistem drainase yang baik sangat dibutuhkan untuk mencegah kelembaban berlebih yang bisa menimbulkan masalah. Pemangkasan juga berperan besar dalam pencegahan penyakit. Dengan memangkas daun yang sudah tua atau rusak, kita menciptakan ruang yang lebih baik bagi sirkulasi udara di sekitar tanaman, yang membantu mengurangi kelembaban yang bisa memicu berkembangnya patogen. Selain itu, pemantauan secara rutin terhadap tanaman sangat penting. Dengan melakukan pemeriksaan berkala, kita dapat mendeteksi gejala awal penyakit sebelum menyebar lebih luas. Sebagai contoh, jika terdapat tanda-tanda seperti bercak kecil pada daun, langkah cepat dalam pengendalian seperti pemberian fungisida atau pengobatan organik bisa dilakukan. Pemantauan yang teliti juga bisa mencakup penggunaan teknologi, seperti aplikasi atau alat pendeteksi penyakit, yang membantu dalam mendiagnosis penyakit secara lebih akurat dan lebih cepat. Terakhir, rotasi tanaman juga menjadi salah satu metode pencegahan yang penting, terutama untuk menghindari penumpukan patogen tertentu di dalam tanah. Semua langkah pencegahan ini dapat memastikan bahwa daun sawit tetap sehat, produktif, dan terlindungi dari berbagai penyakit yang dapat menghambat pertumbuhannya.",
                    detailRecommendation = "Menjaga kondisi daun sawit tetap sehat memerlukan pendekatan yang komprehensif dan teratur dalam perawatan tanaman. Pemupukan yang tepat adalah salah satu hal yang paling krusial dalam perawatan tanaman sawit. Pemupukan harus dilakukan dengan mempertimbangkan kebutuhan spesifik tanaman terhadap unsur hara makro dan mikro, seperti nitrogen, fosfor, kalium, dan magnesium. Tanpa pemupukan yang seimbang, daun sawit akan kekurangan nutrisi yang dibutuhkan untuk tumbuh dengan baik, yang akhirnya dapat mempengaruhi kesehatan daun. Selain itu, pengairan yang tepat juga sangat penting; sawit memerlukan pasokan air yang cukup, namun jika terlalu banyak air atau genangan, akar bisa terendam dan menyebabkan tanaman menjadi rentan terhadap penyakit akar atau pembusukan. Pemangkasan juga merupakan aspek penting dalam perawatan daun sawit. Dengan memangkas daun-daun yang sudah tua atau rusak, kita tidak hanya menjaga kebersihan kebun tetapi juga mengurangi kemungkinan penyebaran penyakit atau serangan hama. Untuk mengatasi hama, pengendalian dengan cara yang tepat sangat diperlukan. Penggunaan pestisida yang ramah lingkungan atau teknik pengendalian hayati bisa diterapkan untuk mengurangi dampak negatif pada tanaman. Terakhir, pemantauan rutin terhadap kondisi daun dan seluruh tanaman juga sangat diperlukan, karena deteksi dini terhadap gejala penyakit atau gangguan lain dapat mencegah masalah menjadi lebih parah. Secara keseluruhan, semua tindakan ini harus dilakukan secara teratur dan dengan penuh perhatian agar daun sawit tetap dalam kondisi sehat dan produktif.",
                    date = "2025-02-10"
                ),
            )
            diseaseData.forEach{ dao.insert(it) }
        }
    }


}