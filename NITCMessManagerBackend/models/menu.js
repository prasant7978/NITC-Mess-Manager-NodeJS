const mongoose = require('mongoose')

const menuSchema = new mongoose.Schema({
    day: {type: 'string', required: true},
    breakfast: {type: 'string'},
    lunch: {type: 'string'},
    dinner: {type: 'string'}
})

module.exports = mongoose.model('menu', menuSchema)