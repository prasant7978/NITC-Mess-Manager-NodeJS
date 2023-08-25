const Contractor = require('../../models/contractor')
const Feedback = require('../../models/feedback')

module.exports = async(req, res) => {
    try{
        await Contractor.updateOne(
            {
                _id: req.id,
                "feedbackReceived._id": req.query.feedbackId
            },{
                $pull: {
                    "feedbackReceived": {_id: req.query.feedbackId}
                }
        })
        res.status(200).send(true)
    }catch(err){
        console.log("Error: " + err);
        res.status(500).send(false)
    }
}