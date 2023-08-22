const Contractor = require('../../models/contractor')
// const mongoose = require('mongoose')
const Menu = require('../../models/menu')

module.exports = async(req, res) => {
    try{
        // console.log("aaya");
        var contractor = await Contractor.findOne({_id: req.id,
            "messMenu.day": req.body.day}).exec()
        
        if(!contractor || contractor == null){
            contractor = await Contractor.findOne({_id: req.id}).exec()
            // const id = mongoose.Types.ObjectId()
            const menu = new Menu({day: req.body.day, 
                breakfast: req.body.breakfast, 
                lunch: req.body.lunch, 
                dinner: req.body.dinner})
            contractor.messMenu.push(menu)
            await contractor.save()
            res.status(200).send(true)
        }
        else{
            await Contractor.updateOne(
                {
                _id: req.id,
                "messMenu.day": req.body.day,
            },{
                $set: {
                    "messMenu.$[messDay]": req.body
                }
            },{
                arrayFilters: [{
                    "messDay.day": req.body.day
                }]
            })
        }
    }catch(err){
        console.log(err);
    }
}