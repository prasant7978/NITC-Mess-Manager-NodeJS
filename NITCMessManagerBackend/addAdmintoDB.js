const Admin = require('./models/admin')
const mongoose = require("mongoose");
const url = 'mongodb://127.0.0.1:27017/NITCMessManager'

mongoose.connect(url)
const con = mongoose.connection

const admin = new Admin({
    adminName: "Prasant Sethi",
    adminEmail: "prasant@gmail.com",
    adminPassword: "admin123",
    userType: "Admin"
})

try{
    admin.save().then(function(adminSaved){
        if(!adminSaved){
            console.log("admin not created.....")
        }
        else{
            console.log("admin created.....")
        }
    })
}catch(err){
    console.log("Error: ", err)
}