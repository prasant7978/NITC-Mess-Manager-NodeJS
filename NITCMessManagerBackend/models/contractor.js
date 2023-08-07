const mongoose = require('mongoose')
const menu = require('./menu')
const feedback = require('./feedback')
const config = require('config')
const jwt = require('jsonwebtoken')

const contractorSchema = new mongoose.Schema({
    contractorName: {type: 'string', required: true},
    contractorEmail: {type: 'string', required: true},
    contractorPassword: {type: 'string', required: true},
    messName: {type: 'string', required: true},
    foodType: {type: 'string'},
    capacity: {type: 'number'},
    availability: {type: 'number'},
    costPerDay: {type: 'number'},
    totalDue: {type: 'number', default: 0},
    userType: {type: 'string', required: true},
    studentEnrolled: [String],
    messMenu: [menu.schema],
    feedbackReceived: [feedback.schema]
})

contractorSchema.methods.generateAuthToken = function(){
    const jwtGenerated = jwt.sign({_id: this._id, email: this.email}, config.get('PRIVATE_KEY'))
    return jwtGenerated
}

module.exports = mongoose.model('contractor', contractorSchema)