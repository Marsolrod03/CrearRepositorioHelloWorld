package com.example.moviedatabase

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.common_ui.databinding.ActivityMainBinding
import com.example.home_ui.FragmentHome
import com.example.data.AirplaneModeReceiver
import com.example.ui.details.DetailsActorFragment
import dagger.hilt.android.AndroidEntryPoint
import com.example.common_ui.R as CR

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var launchedDeeplink = false

    private lateinit var airplaneModeReceiver: AirplaneModeReceiver

    companion object {
        private const val NOTIFICATION_PERMISSION_REQUEST_CODE = 1000
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        notificationPermission()

        requestLocationPermission()

        airplaneModeReceiver = AirplaneModeReceiver { isAirplaneModeOn ->
            if (isAirplaneModeOn) {
                Toast.makeText(this, "Modo avión activado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Modo avión desactivado", Toast.LENGTH_SHORT).show()
            }
        }
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        registerReceiver(airplaneModeReceiver, filter)

        deepLink(intent)

        if (savedInstanceState==null && !launchedDeeplink) {
            supportFragmentManager.beginTransaction()
                .replace(CR.id.navHostFragment,
                    FragmentHome()
                )
                .commit()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent != null) {
            deepLink(intent)
        }
    }

    private fun notificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    NOTIFICATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }


    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            NOTIFICATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        R.string.concededPermission,
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val showMessage = ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.POST_NOTIFICATIONS
                    )

                    if (!showMessage) {
                        AlertDialog.Builder(this)
                            .setTitle(R.string.permissionNeed)
                            .setMessage(R.string.goSettings)
                            .setPositiveButton(R.string.settings) { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", packageName, null)
                                }
                                startActivity(intent)
                            }
                            .setNegativeButton(R.string.cancel, null)
                            .show()
                    } else {
                        Toast.makeText(
                            this,
                            R.string.notConcededPermission,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        getString(R.string.permiso_de_ubicaci_n_concedido),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val showRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    if (!showRationale) {
                        AlertDialog.Builder(this)
                            .setTitle("Permiso requerido")
                            .setMessage("Para usar esta función, debes habilitar el permiso de ubicación en ajustes.")
                            .setPositiveButton("Ir a ajustes") { _, _ ->
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", packageName, null)
                                }
                                startActivity(intent)
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                    } else {
                        Toast.makeText(
                            this,
                            "Permiso de ubicación no concedido",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun deepLink(intent: Intent) {
        val data = intent.data
        if (data != null && data.host == "moviedb.com") {
            val pathSegments = data.pathSegments
            if (pathSegments.isNotEmpty() && pathSegments[0] == "actor") {
                val actorId = pathSegments.getOrNull(1)
                if (actorId != null) {
                    launchedDeeplink = true
                    val fragment = DetailsActorFragment.newInstance(actorId)
                    supportFragmentManager.beginTransaction()
                        .replace(CR.id.navHostFragment, fragment)
                        .addToBackStack(null)
                        .commit()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(airplaneModeReceiver)
    }
}