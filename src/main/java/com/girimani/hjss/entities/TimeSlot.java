package com.girimani.hjss.entities;

public enum TimeSlot {
    From2To3 {
        @Override
        public String toString() {
            return "2:00 PM - 3:00 PM";
        }
    },
    From3To4 {
        @Override
        public String toString() {
            return "3:00 PM - 4:00 PM";
        }
    },
    From4To5 {
        @Override
        public String toString() {
            return "4:00 PM - 5:00 PM";
        }
    },
    From5To6 {
        @Override
        public String toString() {
            return "5:00 PM - 6:00 PM";
        }
    },
    From6To7 {
        @Override
        public String toString() {
            return "6:00 PM - 7:00 PM";
        }
    },
}
