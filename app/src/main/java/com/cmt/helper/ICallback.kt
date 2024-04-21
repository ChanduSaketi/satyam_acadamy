package com.cmt.helper

interface ICallback {
    fun delegate(any: Any? = null)
    fun delegates(any: Any) {}
    fun delegates(any1: Any, any2: Any) {}
}