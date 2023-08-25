const Contractor = require('../../models/contractor')
const feedback = require('../../models/feedback')

module.exports = async(req, res) => {
    try{
        const contractor = await Contractor.findOne({_id: req.id}).exec()
        if(contractor) {
            const feedbacks = contractor.feedbackReceived
            if(feedbacks)
                res.status(200).send(feedbacks)
            else{
                console.log("Feedbacks not found")
                res.status(400).send(null)
            }
        }
        else{
            console.log("Contractor not found")
            res.status(400).send(null)
        }
    }catch(err){
        console.log("Error: " + err)
        res.status(400).send(null)
    }
}