const getStudentByEmail = require('../../functions/getStudentByEmail')

module.exports = async(req, res) => {
    const student = await getStudentByEmail(req.id)
    if(student)
        res.status(200).send(student)
    else
        res.status(400).send(null)
}