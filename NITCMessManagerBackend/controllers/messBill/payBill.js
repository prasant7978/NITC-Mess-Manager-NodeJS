const Student = require('../../models/student')
const Contractor = require('../../models/contractor')

module.exports = async(req, res) => {
    try{
        const student = await Student.updateOne(
            {
                _id: req.id
            },{
                $set: {
                    "messBill": 0,
                    "messEnrolled": "",
                    "paymentStatus": "paid"
                }
            }
        )

        if(student.modifiedCount > 0){
            try{
                console.log(req.query.messName + " " + req.query.messBill);
                console.log(typeof req.id);
                const contractor = await Contractor.updateOne(
                    {
                        messName: req.query.messName
                    },{
                        $inc: {
                            "totalEnrolled": -1,
                            "availability": 1,
                            "totalDue": -(req.query.messBill)
                        },
                        $pull: {
                            "studentEnrolled": req.id
                        }
                    }
                )

                if(contractor.modifiedCount > 0){
                    res.status(200).send(true)
                }
                else{
                    console.log("Contractor not updated");
                    res.status(400).send(false)
                }

            }catch(err){
                console.log(err);
                res.status(400).send(false)
            }
        }
        else{
            console.log("student not updated");
            res.status(400).send(false)
        }
        
    }catch(err){
        console.log(err);
        res.status(400).send(false)
    }
}