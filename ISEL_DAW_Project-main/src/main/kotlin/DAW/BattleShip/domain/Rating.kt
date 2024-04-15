package DAW.BattleShip.domain

import kotlin.math.pow


object Rating {
    //To increase or decrease the newRating, offset this constant
    private const val constant = 30

     private fun probability(rating1: Double, rating2: Double): Double {
         return 1.0 * 1.0 / (1 + 1.0 * 10.0.pow(
             (1.0 * (rating1 - rating2) / 400)))
     }

    fun newRatings(winnerRating : Double, loserRating: Double) : Pair<Double,Double>{
        val probabilityOfWinner = probability(winnerRating,loserRating)
        val probabilityOfLoser = probability(loserRating,winnerRating)

        val winnerNewRating = winnerRating + constant * (1 - probabilityOfWinner)
        val loserNewRating = loserRating + constant * (0 - probabilityOfLoser)

        return Pair(winnerNewRating,loserNewRating)
    }
 }