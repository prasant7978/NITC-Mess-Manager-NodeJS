const Contractor = require('../../models/contractor')
const Feedback = require('../../models/feedback')

module.exports = async(req, res) => {
    try{
        var contractor = await Contractor.findOne({messName: req.query.messName}).exec()
        if(contractor){
            const feedback = new Feedback({
                feedbackMessage: req.body.feedbackMessage,
                studentName: req.body.studentName
            })
            contractor.feedbackReceived.push(feedback)
            await contractor.save()
            res.status(200).send(true)
        }
        else{
            console.log("Contractor not found")
            res.status(400).send(false)
        }
    }catch(err){
        console.error(err)
        res.status(400).send(false)
    }
}