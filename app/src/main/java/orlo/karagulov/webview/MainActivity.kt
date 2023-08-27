package orlo.karagulov.webview

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.onesignal.OneSignal
import com.onesignal.debug.LogLevel

const val ONESIGNAL_APP_ID = "0bef5a9d-c7ca-4376-b4f6-4c892872bd80"


class MainActivity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestQueue = Volley.newRequestQueue(this)
        checkInternetConnection()
        fetchPhpScript()

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.Debug.logLevel = LogLevel.VERBOSE
        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID)
    }


    private fun fetchPhpScript() {
        val url = "https://dat003.ru/clock.php?id=8hd6mmcpjgmxv7uk9dab"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val urlFromResponse = response.optString("url")
                if (!urlFromResponse.isNullOrEmpty()) {
                    // Показываем WebView с указанным URL
                    showWebView(urlFromResponse)
                } else {
                    // В случае отсутствия URL показываем экран регистрации
                    showRegistrationScreen()
                }
            },
            { error ->
                // Обработка ошибок
            }
        )

        requestQueue.add(jsonObjectRequest)
    }

    private fun checkInternetConnection() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo == null || !networkInfo.isConnected) {
            showNoInternetDialog()
        }
    }

    private fun showNoInternetDialog() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Нет интернета")
        alertDialog.setMessage("Пожалуйста, проверьте ваше подключение к интернету.")

        alertDialog.setPositiveButton("OK") { dialog: DialogInterface, which: Int ->
            finish()
        }

        alertDialog.show()
    }

    private fun showWebView(url: String) {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)
        startActivity(intent)
    }

    private fun showRegistrationScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        // Создаем диалоговое окно
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Выход")
        alertDialog.setMessage("Вы уверены, что хотите выйти из приложения?")

        alertDialog.setPositiveButton("Да") { dialog: DialogInterface, which: Int ->
            // Закрываем приложение
            finish()
        }

        alertDialog.setNegativeButton("Нет") { dialog: DialogInterface, which: Int ->
            fetchPhpScript()
            // Продолжаем работу в приложении
        }
        alertDialog.show()
    }

}