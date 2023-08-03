const mongoose = require('mongoose')

const feedbackSchema = new mongoose.Schema({
    feedbackMessage: {type: 'string', required: true},
    studentName: {type: 'string', required: true}
})

module.exports = mongoose.model('feedback', feedbackSchema)