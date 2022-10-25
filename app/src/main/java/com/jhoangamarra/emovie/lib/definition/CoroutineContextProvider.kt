package com.jhoangamarra.emovie.lib.definition

import kotlin.coroutines.CoroutineContext

interface CoroutineContextProvider {
    val io: CoroutineContext
    val computation: CoroutineContext
    val main: CoroutineContext
}
