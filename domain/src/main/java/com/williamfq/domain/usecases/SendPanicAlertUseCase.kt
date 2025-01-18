package com.williamfq.domain.usecases

import com.williamfq.domain.models.Location
import com.williamfq.domain.models.PanicAlert
import com.williamfq.domain.models.PanicAlertStatus
import com.williamfq.domain.repository.PanicRepository
import javax.inject.Inject

class SendPanicAlertUseCase @Inject constructor(
    private val repository: PanicRepository
) {
    suspend fun invoke(
        message: String,
        userId: String,
        location: Location?
    ) {
        val panicAlert = PanicAlert(
            message = message,
            userId = userId,
            location = location,
            status = PanicAlertStatus.SENDING // Estado inicial
        )
        repository.sendPanicAlert(panicAlert)
    }
}
