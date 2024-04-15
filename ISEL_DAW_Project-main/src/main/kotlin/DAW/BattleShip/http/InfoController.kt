package DAW.BattleShip.http


import DAW.BattleShip.http.infra.InfoSiren
import DAW.BattleShip.http.model.HomeOutput
import DAW.BattleShip.http.model.getInformation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import DAW.BattleShip.http.infra.response

@RestController
class InfoController {
    @GetMapping(Uris.ABOUT_US)
    fun getInfo(): ResponseEntity<*> {
        println("-----------------ABOUT_US-----------")
        val info = getInformation()
       return InfoSiren.makeSiren(info).response(200)
    }


    @GetMapping(Uris.HOME)
    fun getHome() : ResponseEntity<*>{
        return InfoSiren.makeSiren(HomeOutput.getHome).response(200)
    }
}
