/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.wotaslive

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder

import tv.danmaku.ijk.media.player.IMediaPlayer

class MediaPlayerService : Service() {

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    companion object {
        var mediaPlayer: IMediaPlayer? = null
            set(mp) {
                if (mediaPlayer !== mp) {
                    mediaPlayer?.let {
                        if (it.isPlaying)
                            it.stop()
                        it.release()
                        field = null
                    }
                }
                field = mp
            }

        fun newIntent(context: Context): Intent {
            return Intent(context, MediaPlayerService::class.java)
        }

        fun intentToStart(context: Context) {
            context.startService(newIntent(context))
        }

        fun intentToStop(context: Context) {
            context.stopService(newIntent(context))
        }
    }
}