#pragma once

#import <AppKit/NSOpenGLView.h>
#import <Cocoa/Cocoa.h>
#import <CoreVideo/CoreVideo.h>
#import <OpenGL/gl.h>

#include "StarbrightOpenGLRenderer.h"

@interface StarbrightNSOpenGLView : NSOpenGLView
{
  CVDisplayLinkRef display_link;
  int              display_width;
  int              display_height;
  double           time_debt;
  double           previous_frame_timestamp;
  BOOL             initialized_starbright;
  Starbright::Renderer* renderer;
}

- (void)startDisplayLink;
- (void)stopDisplayLink;
- (void)onCreate;
- (void)onDraw;
- (void)onStart;
- (void)onStop;
- (void)onUpdate;

@end