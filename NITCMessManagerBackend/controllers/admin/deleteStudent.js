const Student = require('../../models/student')

module.exports = async (req, res) => {
    try{
        const result = await Student.deleteOne({_id: req.query.studentId})
        
        if(result.deletedCount == 1){
            console.log("successfully deleted the student")
            res.status(200).send(true)
        }
        else{
            console.log("no student deleted")
            res.status(400).send(false)
        }
    }catch(err){
        console.log("Erro: " + err)
        res.status(400).send(false)
    }
}