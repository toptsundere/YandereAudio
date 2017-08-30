package com.meli.pdesire.yandereservice.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle


@SuppressLint("Registered")

/**
 * Created by PDesire on 8/27/17.
 */
object YandereCommandHandler : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private var useSecureReplace: Boolean = false

    fun setSecureReplace (set : Boolean) {
        useSecureReplace = set
    }

    fun getSecureReplace() : Boolean {
        return useSecureReplace
    }

    fun secure_replace(source_directory : String, source_file : String, destination : String) : Int {

        if (getSecureReplace()) {
            val md5hashsum = YandereCryptography.fileToMD5(destination + "/" + source_file);

            YandereRootUtility.sudo("cp " + source_directory + "/" + source_file + " " + destination)

            val secondmd5hashsum = YandereCryptography.fileToMD5(destination + "/" + source_file);

            if (md5hashsum.equals(secondmd5hashsum))
                return 1

            return 0

        } else {
            YandereRootUtility.sudo("cp " + source_directory + "/" + source_file + " " + destination)
            return 1
        }
    }

    @Override
    fun copy(source_directory : String,
             source_file : String,
             destination : String) : Int {
        var success : Int = 0

        mount_rw()
        success = secure_replace(source_directory, source_file, destination)
        mount_ro()

        if (success == 1)
            return 1
        else
            return 0
    }

    fun mount_rw () {
        YandereRootUtility.mount_rw_rootfs()
        YandereRootUtility.mount_rw_system()
    }

    fun mount_ro () {
        YandereRootUtility.mount_ro_rootfs()
        YandereRootUtility.mount_ro_system()
    }

    fun callHeavybass (apply : Boolean) : Int {
        if (apply) {
            return copy("/system/Yuno/heavybass",
                    "srs_processing.cfg",
                    "/system/etc/srs")
        } else {
            return copy("/system/Yuno/stock",
                    "srs_processing.cfg",
                    "/system/etc/srs")
        }
    }

    fun callYumeEngine() {
        copy("/system/Yuno/Engines/Yume/Final/etc",
                "audio_effects.conf",
                "/system/etc")
        copy("/system/Yuno/Engines/Yume/Final/vendor",
                "audio_effects.conf",
                "/system/vendor/etc")
    }

    fun callMeliEngine(){
        copy("/system/Yuno/Engines/Meli/etc",
                "audio_effects.conf",
                "/system/etc")
        copy("/system/Yuno/Engines/Meli/vendor",
                "audio_effects.conf",
                "/system/vendor/etc")

    }

    fun callReboot() {
        YandereRootUtility.sudo("reboot")
    }

    fun callPDesireAudio (activation : Int) {
        YandereRootUtility.sudo("echo " + activation.toString() + " " + YanderePDesireAudioAPI.getPDesireAudio())
    }

    fun callPDesireAudioStatic (activation : Int) {
        YandereRootUtility.sudo("echo " + activation.toString() + " " + YanderePDesireAudioAPI.getPDesireAudioStatic())
    }
}