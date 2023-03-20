package com.example.foosball.domain.scheduler

import io.reactivex.rxjava3.core.Scheduler

interface PostExecutionScheduler {
    fun provide(): Scheduler
}