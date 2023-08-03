const mongoose = require('mongoose')

const studentSchema = new mongoose.Schema({
    studentName: {type: 'string', required: true},
    studentEmail: {type: 'string', required: true},
    studentPassword: {type: 'string', required: true},
    studentRollNo: {type: 'string', required: true},
    studentGender: {type: 'string', required: true},
    messEnrolled: {type: 'string'},
    messBill: {type: 'number', default: 0},
    paymentStatus: {type: 'string', required: true},
    userType: {type: 'string', required: true},
})

module.exports = mongoose.model('student', studentSchema)