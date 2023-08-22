const Contractor = require('../../models/contractor')

module.exports = async(req, res) => {
    const contractor = await Contractor.findOne({messName: req.query.messName}).exec()
    if(contractor){
        const menu = contractor.messMenu.find(menu => menu.day == req.query.day)
        if(!menu)
            res.status(200).send(JSON.stringify(null))
        else
            res.status(200).send(menu)
    }
    else
        res.status(400).send(null)
}