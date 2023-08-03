const express = require("express")
const mongoose = require("mongoose");
const url = 'mongodb://127.0.0.1:27017/NITCMessManager'

mongoose.connect(url)
const con = mongoose.connection

con.on('open', () => {
    console.log("db connected...")
})

const app = express()

app.use(express.json())

app.listen(3000, () => {
    console.log("listening on port 3000 ...")
})