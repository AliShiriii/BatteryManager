package com.example.batterymanager.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.batterymanager.databinding.ActivitySplashBinding
import java.util.*
import kotlin.concurrent.timerTask

class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val textArray = arrayOf(
            "Make Your Battery Powerful",
            "Make Your Battery Safe",
            "Make Your Battery Faster",
            "Make Your Battery Powerful",
            "Manage Your Phone Battery",
            "Notify When Your Phone Is Full charge"
        )

        var i = 1

        for (i in 1..6) {
            helpTextGenerator((i * 1000).toLong(), textArray[i - 1])
        }

        goToMainActivity()
    }

    private fun goToMainActivity() {

        Timer().schedule(timerTask {
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()

        }, 7000)

    }

    private fun helpTextGenerator(delayTime: Long, helpText: String) {

        Timer().schedule(timerTask {

            runOnUiThread(timerTask {

                binding.helpTxt.text = helpText

            })

        }, delayTime)

        Timer().schedule(timerTask {
            runOnUiThread(timerTask {

                binding.helpTxt.text = helpText

            })

        }, 2000)
        Timer().schedule(timerTask {
            runOnUiThread(timerTask {

                binding.helpTxt.text = helpText

            })

        }, 3000)
        Timer().schedule(timerTask {
            runOnUiThread(timerTask {

                binding.helpTxt.text = helpText

            })

        }, 4000)
        Timer().schedule(timerTask {
            runOnUiThread(timerTask {

                binding.helpTxt.text = helpText

            })

        }, 5000)
        Timer().schedule(timerTask {
            runOnUiThread(timerTask {

                binding.helpTxt.text = helpText

            })

        }, 6000)
    }
}
