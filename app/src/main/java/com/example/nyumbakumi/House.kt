package com.example.nyumbakumi

class House {
    var houseNumber:String = ""
    var houseSize:String = ""
    var housePrice:String = ""
    var userId:String = ""
    var houseId:String = ""

    constructor(
        houseNumber: String,
        houseSize: String,
        housePrice: String,
        userId: String,
        houseId: String
    ) {
        this.houseNumber = houseNumber
        this.houseSize = houseSize
        this.housePrice = housePrice
        this.userId = userId
        this.houseId = houseId
    }
    constructor()
}