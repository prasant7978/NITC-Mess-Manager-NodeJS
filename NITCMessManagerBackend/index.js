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

const authenticationRouter = require("./routes/auth.js")
app.use('/auth', authenticationRouter)

const getProfileRouter = require("./routes/profile.js")
app.use('/getProfile', getProfileRouter)

const messMenuRouter = require("./routes/messMenu.js")
app.use('/messMenu', messMenuRouter)

app.listen(3000, '192.168.238.154', () => {
    console.log("listening on port 3000 ...")
})