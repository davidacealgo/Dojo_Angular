package controllers

import javax.inject._

import models.Place
import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.api.mvc.Result._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
/*Controller to handle the actions*/
/*action: DefaultActionBuilder,
parse: PlayBodyParsers,
messagesApi: MessagesApi*/
@Singleton
class HomeController @Inject() (val controllerComponents: ControllerComponents) extends BaseController {

  //val places2: List[Place] = Place(1, "Robledo") :: Place(2, "Medellin") :: Place(3, "Barbosa") :: Nil

  var places = List(
    Place(1, "Barbosa", Some("cualquier cosa")),
    Place(2, "Medellín", Some("otra cosa")),
    Place(3, "Barbosa", None)
  )


  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */

    def listPlaces = Action {
    val json = Json.toJson(places)
    Ok(json)
  }



  def addPlace() = Action {
    request => val json = request.body.asJson.get

    json.validate[Place] match{
      case success: JsSuccess[Place] =>
        places = places :+ success.get
        Ok(Json.toJson(Map("response" -> "ingresado..")))
      case error:JsError => BadRequest(Json.toJson(Map("error"->"error")))
    }
  }

  def removePlace(id:Int) = Action {
    places = places.filterNot(_.id == id)
    Ok(Json.toJson(
      Map("message" -> "Borrado exitoso")
    ))
  }

  def updatePlace = Action{
    request => val json = request.body.asJson.get
      json.validate[Place] match{
        case success:JsSuccess[Place]=>
          val newPlace = Place(success.get.id,success.get.name,success.get.description)
          places = places.map(x => if(x.id == success.get.id)newPlace
          else x)
          Ok(Json.toJson(Map("Response"->"Actualizado..")))
        case error: JsError => BadRequest(Json.toJson(Map("Error"->"Error")))
      }
  }

  /*def updatePlace() = Action { implicit request =>
    val bodyAsJson = request.body.asJson.get

    bodyAsJson.validate[Place] match {
      case success: JsSuccess[Place] =>
        var newPlace = Place(success.get.id, success.get.name)
        places = places.map(x => if (x.id == success.get.id) newPlace else x)
        Ok(Json.toJson(
          Map("message" -> "Actualizacion exitosa")
        ))
      case e:JsError => BadRequest(Json.toJson(
        Map("error" -> "No se pudo actualizar", "description" -> "Bad parameters")))
    }
  }*/


}