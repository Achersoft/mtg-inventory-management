module.exports = function (grunt) {
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        copy:{
            build:{
                cwd:'app',
                src:['**'],
                dest:'/Users/shaun/AppData/Roaming/NetBeans/8.1/apache-tomcat-8.0.27.0_base/webapps/titan/',
                expand:true
            }
        },
        clean:{
            build:{
                src:'build'
            }
        }
    });
    grunt.loadNpmTasks('grunt-contrib-copy');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.registerTask(
        'build',
        'Compiles all the assets and copies the files to the build directory.',
       [ 'clean', 'copy' ]
    );
};