package com.example.gscustomphoneservicebug

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class FileUtils {
    companion object {
        private const val GS_PHONE_SERVICE_LOG = "gsphoneservice"
        private const val NOTIFICATIONS_LOGGING_FILENAME = "notifications_log_dump.txt"

        fun writeToExternalNotificationsLog(context: Context, textToWrite: String) {
            writeToDevLog(context, textToWrite, NOTIFICATIONS_LOGGING_FILENAME)
        }

        /**
         *  Appends the textToWrite to the argument file name. If the file name does not already
         *  exist in the hldev folder it will be created.  A time stamp is always prepended to text
         *  added.
         */
        private fun writeToDevLog(context: Context, textContent: String, fileName: String) {
            val rootPath = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)?.absolutePath

            val notificationsLogDumpPath = rootPath + File.separator + GS_PHONE_SERVICE_LOG

            val file = File(notificationsLogDumpPath, fileName)

            val textToWrite = getLogFileDateTimeStampString() + "    " + textContent + "\n"

            try {
                val pFile = file.parentFile

                if (pFile != null && !pFile.exists()) {
                    file.parentFile!!.mkdirs()
                }
                if (!file.exists()) {
                    file.createNewFile()
                }

                if (file.canWrite()) {
                    file.appendText(textToWrite, Charset.forName("UTF-8"))
                }
            } catch (e: IOException) {
                Log.e(e.toString(),"Unable to write to log file. Were write permissions enabled for the app?")
            }
        }

        private fun getLogFileDateTimeStampString(): String {
            val format = SimpleDateFormat("MMM dd yyyy HH:mm:ss", Locale.US)
            val now = Date()
            return format.format(now)
        }
    }
}