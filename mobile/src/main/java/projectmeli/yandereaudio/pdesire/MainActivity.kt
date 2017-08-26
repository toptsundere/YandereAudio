package projectmeli.yandereaudio.pdesire

/**
 * Created by PDesire on 20.05.2017.
 */

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.ImageView
import android.view.animation.AnimationUtils
import android.view.animation.AnimationSet
import android.widget.Toast
import com.meli.pdesire.yandereservice.PDesireAudioActivity
import com.meli.pdesire.yandereservice.SettingsActivity
import com.meli.pdesire.yandereservice.framework.YandereFileManager
import com.meli.pdesire.yandereservice.framework.YanderePackageManager
import com.meli.pdesire.yandereservice.listeners.YandereWearableApplyListener


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private fun closedReleaseTest () {
        if (YanderePackageManager.closedReleaseTest(this)) {
            Toast.makeText(this, getString(R.string.security_error),
                    Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun meliInstalledCheck () {
        if(!YandereFileManager.fileCheck("/system/meli.prop")) {
            val messageOutput = AlertDialog.Builder(this)
            messageOutput.setTitle(getString(R.string.meli_not_installed))
                    .setMessage(getString(R.string.no_project_meli_installed))
                    .setPositiveButton(getString(R.string.go_to_thread)) { dialog, which ->
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))
                        startActivity(intent)
                    }
                    .setNegativeButton(getString(R.string.ignore)) { dialog, which ->
                        // do nothing
                    }
                    .setIcon(R.mipmap.ic_launcher)
                    .create()
                    .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.setDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<NavigationView>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)

        val fadeInAnimationSet = AnimationSet(false)

        val meliLogoView = findViewById<ImageView>(R.id.meli_logo_view) as ImageView
        val meliDescriptionView = findViewById<ImageView>(R.id.meli_description_view) as ImageView
        val fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        fadeInAnimationSet.addAnimation(fadeInAnimation)
        meliLogoView.startAnimation(fadeInAnimationSet)
        meliDescriptionView.startAnimation(fadeInAnimationSet)

        closedReleaseTest()
        meliInstalledCheck()

        val intent = Intent(applicationContext, YandereWearableApplyListener::class.java)
        startService(intent)
    }



    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_service) {
            val intent = Intent(applicationContext, SettingsActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.nav_pdesireaudio) {
            val intent = Intent(applicationContext, PDesireAudioActivity::class.java)
            startActivity(intent)
        } else if (id == R.id.light_theme) {
            this.setTheme(R.style.AppTheme)
            recreate()
        } else if (id == R.id.dark_theme) {
            this.setTheme(R.style.AppThemeDark)
            recreate()
        } else if (id == R.id.nav_contact_xda) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/crossdevice-dev/sony/soundmod-project-desire-feel-dream-sound-t3130504"))
            startActivity(intent)
        } else if (id == R.id.nav_contact_pdesire) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://forum.xda-developers.com/member.php?u=6126659"))
            startActivity(intent)
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
}
