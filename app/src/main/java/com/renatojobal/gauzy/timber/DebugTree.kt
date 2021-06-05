package com.renatojobal.gauzy.timber

import timber.log.Timber


/**
 * Timber tree using in the debug variant.
 *
 * For now er are not sending the fails in the debug mode to Firebase Crashlytics
 */
open class DebugTree : Timber.DebugTree() {


    /**
     * Custom the message that appears in the console
     */
    override fun createStackElementTag(element: StackTraceElement): String? {
        return super.createStackElementTag(element) + ':' + element.lineNumber
    }

    /** Return whether a message at `priority` or `tag` should be logged.  */
    override fun isLoggable(tag: String?, priority: Int): Boolean {
        return true // In debug mode we log everything
    }
}
