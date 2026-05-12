cmake_minimum_required(VERSION 3.10)
project(Craps)

file(GLOB SOURCES "${CMAKE_CURRENT_SOURCE_DIR}/src/*.c")

include_directories(include/)

add_executable(Craps ${SOURCES})

set_target_properties(Craps PROPERTIES 
                      RUNTIME_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}")

target_compile_options(Craps PRIVATE 
    "-Wall" 
    "-fdiagnostics-color=always"
    "-g"
)