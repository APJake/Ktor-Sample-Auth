package com.apjake.utils.validators

import com.apjake.utils.base.BaseValidator
import java.util.regex.Pattern

object IsValidUrl: BaseValidator<String?>() {
    override fun check(data: String?): Boolean {
        val regex = ("((http|https)://)(www.)?"
                + "[a-zA-Z0-9@:%._\\+~#?&//=]"
                + "{2,256}\\.[a-z]"
                + "{2,6}\\b([-a-zA-Z0-9@:%"
                + "._\\+~#?&//=]*)")
        val p: Pattern = Pattern.compile(regex)
        if (data == null) {
            return false
        }
        return p.matcher(data).matches()
    }
}