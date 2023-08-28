const Contractor = require('./models/contractor')
const mongoose = require('mongoose')
const url = 'mongodb://127.0.0.1:27017/NITCMessManager'

mongoose.connect(url)
const con = mongoose.connection

const contractor = new Contractor({
    contractorName: "Prashant Rathee",
    contractorEmail: "prashant@gmail.com",
    contractorPassword: "prashant123",
    messName: "XYZ",
    foodType: "North Indian Veg & Non-Veg",
    capacity: 500,
    availability: 500,
    costPerDay: 130,
    userType: "Contractor"
})

contractor.save().then(function(contractorSaved){
        if(!contractorSaved)
            console.log("contractor not saved...")
        else
            console.log("contractor created...")
    }).catch(function(err){
        console.log("database error...")
    }
)