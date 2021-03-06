/*
 * Copyright (C) 2017-2018 Tristan Marsell, All rights reserved.
 *
 * This code is licensed under the BSD-3-Clause License
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.meli.pdesire.yanderecore.framework

/**
 * Created by PDesire on 8/27/17.
 */
object YandereCommandHandler {

    @Override
    fun copy(source_directory : String,
             source_file : String,
             destination : String) {

        YandereRootUtility.sudo("cp " + source_directory + "/" + source_file + " " + destination)
    }

    fun mount_rw () {
        YandereRootUtility.mount_rw_rootfs()
        YandereRootUtility.mount_rw_system()
    }

    fun mount_ro () {
        YandereRootUtility.mount_ro_rootfs()
        YandereRootUtility.mount_ro_system()
    }

    fun callHeavybass (apply : Boolean) {
        if (apply) {
            copy("/system/Yuno/heavybass",
                    "srs_processing.cfg",
                    "/system/etc/srs")
        } else {
            copy("/system/Yuno/stock",
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

    fun callPDAECMagic() {
        copy("/system/Yuno/PDAEC",
                "libbundlewrapper.so",
                "/system/lib/soundfx")
        copy("/system/Yuno/PDAEC",
                "libreverbwrapper.so",
                "/system/lib/soundfx")
        copy("/system/Yuno/PDAEC",
                "libldnhncr.so",
                "/system/lib/soundfx")
    }

    fun removePDAECMagic() {
        copy("/system/Yuno/Stock",
                "libbundlewrapper.so",
                "/system/lib/soundfx")
        copy("/system/Yuno/Stock",
                "libreverbwrapper.so",
                "/system/lib/soundfx")
        copy("/system/Yuno/Stock",
                "libldnhncr.so",
                "/system/lib/soundfx")
    }

    fun vendorizeHTC() {
        copy("/system/Yuno/HTC/vendor",
                "htc_audio_effects.conf",
                "/system/etc")
    }

    fun etcizeHTC() {
        copy("/system/Yuno/HTC/etc",
                "htc_audio_effects.conf",
                "/system/etc")
    }

    fun owningHTC() {
        copy("/system/Yuno/HTC/own",
                "htc_audio_effects.conf",
                "/system/etc")
    }

    fun stockingHTC() {
        copy("/system/Yuno/HTCstock",
                "htc_audio_effects.conf",
                "/system/etc")
    }

    fun callReboot() {
        YandereRootUtility.sudo("reboot")
    }


}