package com.tymex.core.domain.util


sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E: com.tymex.core.domain.util.Error>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> {
            Result.Error(error)
        }
        is Result.Success -> {
            Result.Success(map(data))
        }
    }
}

fun <T, E: Error> Result<T, E>.asEmptyDataResult() : EmptyResult<E> {
    return map { }
}

fun <T, E : Error> Result<T, E>.asEmptyListResult(): EmptyListResult<T, E> {
    return Result.Success(emptyList())
}

typealias EmptyResult<E> = Result<Unit, E>
typealias EmptyListResult<T, E> = Result<List<T>, E>