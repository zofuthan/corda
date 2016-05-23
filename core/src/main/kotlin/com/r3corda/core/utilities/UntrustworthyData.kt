package com.r3corda.core.utilities

/**
 * A small utility to approximate taint tracking: if a method gives you back one of these, it means the data came from
 * a remote source that may be incentivised to pass us junk that violates basic assumptions and thus must be checked
 * first. The wrapper helps you to avoid forgetting this vital step. Things you might want to check are:
 *
 * - Is this object the one you actually expected? Did the other side hand you back something technically valid but
 *   not what you asked for?
 * - Is the object disobeying its own invariants?
 * - Are any objects *reachable* from this object mismatched or not what you expected?
 * - Is it suspiciously large or small?
 */
class UntrustworthyData<T>(private val fromUntrustedWorld: T) {
    val data: T
        @Deprecated("Accessing the untrustworthy data directly without validating it first is a bad idea")
        get() = fromUntrustedWorld

    @Suppress("DEPRECATION")
    inline fun <R> validate(validator: (T) -> R) = validator(data)
}