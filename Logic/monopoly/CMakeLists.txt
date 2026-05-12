cmake_minimum_required(VERSION 3.10)
project(Monopoly)

file(GLOB SOURCES "${CMAKE_CURRENT_SOURCE_DIR}/src/*.cpp")

include_directories(include/)

add_executable(Monopoly ${SOURCES})

set_target_properties(Monopoly PROPERTIES 
                      RUNTIME_OUTPUT_DIRECTORY "${CMAKE_CURRENT_SOURCE_DIR}")

target_compile_options(Monopoly PRIVATE 
    "-Wall" 
    "-fdiagnostics-color=always"
    "-g"
)