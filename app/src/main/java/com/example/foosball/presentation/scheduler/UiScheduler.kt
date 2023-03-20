package com.example.foosball.presentation.scheduler

import com.example.foosball.domain.scheduler.PostExecutionScheduler
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import javax.inject.Inject

class UiScheduler @Inject constructor() : PostExecutionScheduler {
    override fun provide(): Scheduler {
        return AndroidSchedulers.mainThread()
    }
}