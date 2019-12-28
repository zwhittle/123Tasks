package com.zachwhittle.a123tasks.util


object Constants {
    const val REGEX_DATE_YYYYMMDD =
        """(19|20)\d\d[- /.](0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$"""
    const val REGEX_DATE_MMDDYYYY =
        """(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.](19|20)\d\d$"""
    const val REGEX_DATE_MMDDYY = """(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])[- /.]\d\d$"""
    const val REGEX_DATE_MMDD = """(0[1-9]|1[012])[- /.](0[1-9]|[12][0-9]|3[01])$"""
}