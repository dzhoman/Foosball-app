package com.example.foosball.data.scheduler

import com.example.foosball.domain.scheduler.ExecutionScheduler
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BackgroundScheduler @Inject constructor() : ExecutionScheduler {
    override fun provide(): Scheduler {
        return Schedulers.io()
    }
}