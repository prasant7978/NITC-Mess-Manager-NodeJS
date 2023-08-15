const Student = require('../models/student')
module.exports = async(id) => {
    const student = await Student.findOne({_id: id}).exec()
    if(student)
        return student
    else
        return null
}