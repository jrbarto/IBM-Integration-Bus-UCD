/*
 * Licensed Materials - Property of IBM Corp.
 * IBM UrbanCode Deploy
 * IBM AnthillPro
 * (c) Copyright IBM Corporation 2016. All Rights Reserved.
 *
 * U.S. Government Users Restricted Rights - Use, duplication or disclosure restricted by
 * GSA ADP Schedule Contract with IBM Corp.
 */
import com.urbancode.air.AirPluginTool
import com.urbancode.air.ExitCodeException
import com.urbancode.air.plugin.iib.helper.MQSIHelper

MQSIHelper mqHelper
AirPluginTool apTool = new AirPluginTool(this.args[0], this.args[1])
def props = apTool.getStepProperties()

def installDir = props['installDir'].trim()
def integrationNode = props['integrationNode'].trim()
def deleteQueues = Boolean.valueOf(props['deleteQueues'])
def deleteTrace = Boolean.valueOf(props['deleteTrace'])

try {
    mqHelper = new MQSIHelper(installDir, apTool.isWindows)
}
catch(FileNotFoundException ex) {
    println("Unable to locate mqsi script directory:")
    println(ex.message)
}

try {
    mqHelper.deleteBroker(integrationNode, deleteQueues, deleteTrace)
}
catch(ExitCodeException ex) {
    println("Could not delete integration node:")
    println(ex.message)
    System.exit(1)
}
catch(FileNotFoundException ex) {
    println("Couldn't locate necessary mqsi script file:")
    println(ex.message)
    System.exit(1)
}