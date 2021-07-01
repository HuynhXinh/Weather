package com.example.domain.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface UseCase<out Type : Any, in Params> {

    val dispatcherProvider: DispatcherProvider

    fun run(params: Params): Flow<Result<Type>>

    operator fun invoke(
        scope: CoroutineScope,
        params: Params,
        onResult: (Result<Type>) -> Unit = {}
    ) {
        scope.launch {
            try {
                run(params)
                    .flowOn(dispatcherProvider.io)
                    .collect {
                        onResult(it)
                    }
            } catch (ex: Throwable) {
                onResult(Result.failure(ex))
            }
        }
    }

    class None
}
