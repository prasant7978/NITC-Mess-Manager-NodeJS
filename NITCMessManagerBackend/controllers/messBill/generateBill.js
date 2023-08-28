const Student = require('../../models/student')
const Contractor = require('../../models/contractor')

module.exports = async(req, res) => {
    var contractor = await Contractor.findOne({_id: req.id}).exec()
    if(contractor){
        let costPerDay = contractor.costPerDay
        let days = req.query.totalDays
        let totalCostPerStudent = costPerDay * days

        var studentIds = contractor.studentEnrolled
        console.log(studentIds);

        for(let studentId of studentIds){
            console.log(studentId);
            try{
                await Student.updateOne(
                    {
                        _id: studentId
                    },{
                        $set: {
                            "paymentStatus": "not-paid"
                        },
                        $inc: {
                            "messBill": totalCostPerStudent
                        }
                    }
                )
            }catch(err){
                console.log("Error: " + err)
                res.status(400).send(false)
            }
        }

        let totalMessDue = totalCostPerStudent * contractor.totalEnrolled

        try{
            await Contractor.updateOne(
                {
                    _id: req.id
                },{
                    $set: {
                        "totalDue": totalMessDue
                    }
                }
            )
        }catch(err){
            console.log("Error: " + err)
            res.status(400).send(false)
        }

        res.status(200).send(true)
    }
    else{
        console.log("Contractor not found")
        res.status(400).send(false)
    }
}