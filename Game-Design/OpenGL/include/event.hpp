#ifndef __OPENGL_ENGINE_EVENT_HPP__
#define __OPENGL_ENGINE_EVENT_HPP__

namespace sparka {
    namespace event {
        enum class EVENT_LISTENER {
            KEYDOWN,
            KEYUP,
            CLICK,
            KEYPRESS,
            KEYISDOWN
        };

        class Event {
            public:
            unsigned char key;
        };
    }
}

#endif