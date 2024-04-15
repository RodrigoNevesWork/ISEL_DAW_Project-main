package DAW.BattleShip.repository

import org.springframework.stereotype.Component

@Component
interface TransactionManager {
    fun <R> run(block: (Transaction) -> R): R
}