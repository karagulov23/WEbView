import android.app.Application
import com.yandex.metrica.YandexMetrica
import com.yandex.metrica.YandexMetricaConfig

const val API_key = "b9d8e2d2-efdc-4c7b-8cac-3668fa0ff402"

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val config = YandexMetricaConfig.newConfigBuilder(API_key).build()
        // Initializing the AppMetrica SDK.
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(applicationContext, config)
        // Automatic tracking of user activity.
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this)
    }
}
